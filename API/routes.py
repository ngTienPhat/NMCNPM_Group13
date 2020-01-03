from flask import Blueprint, request, jsonify, render_template

import API
import psycopg2
import uuid
import hashlib
import datetime

account_api = Blueprint('account_api', __name__)

columnNameUserinfo = (
    "userid", "fullname", "useremail", "accountlevel", "membersince", "username", "userpassword", "logIn")
columnNotebook = ("notebookid", "name", "createdate", "owner", "workspaceid", "notes")
columnNote = ("noteid", "title", "createddate", "contentfile", "tags")
columnTag = ("tagid", "tagname", "datecreatetag")


# Funcion Helper

def getPresenTime():
    return datetime.datetime.today().strftime('%Y-%m-%d')


def makeDictWithInfo(columnNames, columnValues):
    return dict(zip(columnNames, columnValues))


@account_api.route('/api', methods=['GET'])
def main_api():
    return render_template('API/API_Index.html')


@account_api.route('/users/create', methods=['POST'])
def create_user():
    uId = uuid.uuid1().hex
    username = request.form['username']
    password = request.form['password']
    fullname = request.form['fullname']
    useremail = request.form['useremail']
    try:
        API.cursor.execute(f"INSERT INTO userinfo(userid, fullname, useremail, accountlevel, membersince, username, userpassword) \
                             VALUES ('{uId}',  '{fullname}', '{useremail}', 0, '{getPresenTime()}' , \
                                     '{username}', '{hashlib.md5(password.encode()).hexdigest()}')")
        API.cursor.execute(f"INSERT INTO notebook(notebookid, name, createddate, owner, workspaceid) \
                           VALUES ('{uuid.uuid1().hex}', 'First Notebook', '{getPresenTime()}', '{uId}', NULL) ")
    except (Exception, psycopg2.Error) as error:
        print('Error while executing to PostgreSQL', error)
        API.cursor.rollback()
        return jsonify({"status": 0})
    API.connection.commit()
    return jsonify({"userid": uId, "status": 1})


@account_api.route('/users/signIn')
def sign_in_user():
    print(request.args)
    user_name = request.args["username"]
    password = request.args["userpwd"]
    API.cursor.execute(
        f"SELECT * FROM userinfo \
          WHERE username = '{user_name}' AND userpassword = '{hashlib.md5(password.encode()).hexdigest()}'"
    )
    record = list(API.cursor.fetchone())
    if record:
        record.append(1)
        return jsonify(makeDictWithInfo(columnNameUserinfo, list(record)))
    return jsonify({"logIn": 0})


@account_api.route('/users/<userid>/getAllInfo')
def getAllInfo(userid):
    API.cursor.execute(
        f"SELECT * FROM notebook WHERE owner = '{userid}'"
    )
    result = []
    notebookRecords = API.cursor.fetchall()
    for notebook in notebookRecords:
        notebook = list(notebook)
        notebookid = notebook[0]
        API.cursor.execute(
            f"SELECT n.* \
              FROM note n INNER JOIN noteinnotebook nn ON n.noteid = nn.noteid \
              WHERE nn.notebookid = '{notebookid}' "
        )
        noteRecords = API.cursor.fetchall()
        notebook.append([])
        for note in noteRecords:
            note = list(note)
            noteid = note[0]
            API.cursor.execute(
                f"SELECT n.tagname \
                  FROM notetag n  \
                  WHERE n.noteid = '{noteid}' "
            )
            tagRecords = list(map(lambda x: {"tagname": x[0]}, API.cursor.fetchall()))
            note.append(tagRecords)
            notebook[-1].append(makeDictWithInfo(columnNote, note))
        result.append(makeDictWithInfo(columnNotebook, notebook))
    return jsonify(result)


@account_api.route('/users/<userid>/notebook', methods=['POST'])
@account_api.route('/users/<userid>/notebook/<notebook>', methods=['PUT', 'DELETE'])
def handle_notebook(userid, notebook=None):
    if request.method == 'POST':
        userid = userid.strip()
        notebookID = uuid.uuid1().hex
        notebookname = request.json["notebookname"]
        try:
            API.cursor.execute(f"INSERT INTO notebook(notebookid, name, createddate, owner, workspaceid) \
                                 VALUES ('{notebookID}', '{notebookname}', '{getPresenTime()}', '{userid}', NULL) ")
            API.cursor.execute(f"INSERT INTO usernotebook(userid, notebookid) VALUES ('{userid}', '{notebookID}')")
        except (Exception, psycopg2.Error) as error:
            print('Error while executing to PostgreSQL', error)
            API.cursor.rollback()
            return jsonify({"status": 0})
        API.connection.commit()
        # ("notebookid", "name", "createdate", "owner", "workspafceid", "status")
        result = makeDictWithInfo(columnNotebook[:-1], [notebookID, notebookname, getPresenTime(), userid, None])
        result["status"] = 1
        return jsonify(result)
    if request.method == "PUT":
        API.cursor.execute(
            f"UPDATE notebook SET name = '{request.json['notebookname']}' WHERE notebookid = '{notebook}'")
        API.connection.commit()
        return jsonify({"status": 1})
    if request.method == "DELETE":
        try:
            API.cursor.execute(f"DELETE FROM usernotebook WHERE userid = '{userid}' AND notebookid = '{notebook}'")
            API.cursor.execute(f"DELETE FROM notebook WHERE notebookid = '{notebook}'")
        except (Exception, psycopg2.Error) as error:
            print('Error while executing to PostgreSQL', error)
            API.connection.rollback()
            return jsonify({"status": 0})
        API.connection.commit()
        return jsonify({"status": 1})


@account_api.route('/users/<userid>/note', methods=['POST'])
@account_api.route('/users/<userid>/note/<note>', methods=['PUT', 'DELETE'])
def handle_note(userid, note=None):
    contentJSON = request.json
    if request.method == "POST":
        # prepare information
        noteid = uuid.uuid1().hex
        notename = contentJSON['notename'] or "Untitle"
        notebook = contentJSON['notebookid']
        notecontent = '{"draftId":0,"items":[{"content":"","itemType":0,"mode":0, \
                                                        "style":0}]}'
        notecreateddate = getPresenTime()

        # Create note in database
        try:
            API.cursor.execute(
                f"INSERT INTO note(noteid, title, createddate, contentfile) \
                  VALUES ('{noteid}', '{notename}', '{notecreateddate}', '{notecontent}')\
                 "
            )
            API.cursor.execute(
                f"INSERT INTO noteinnotebook(noteid, notebookid) VALUES ('{noteid}', '{notebook}')"
            )

        except (Exception, psycopg2.Error) as error:
            print('Error while executing to PostgreSQL', error)
            API.cursor.rollback()
            return jsonify({"status": 0})
        API.connection.commit()
        # "noteid", "title", "createddate", "contentfile", "tags" = columnNotes
        result = makeDictWithInfo(columnNote, [noteid, notename, notecreateddate, "", []])
        result['status'] = 1
        return jsonify(result)

    if request.method == "PUT":
        noteid = note
        notename = contentJSON['notename']
        notecontent = contentJSON['contentfile']
        notebook = contentJSON['notebookid'] or None
        notetags = contentJSON['tags']
        try:
            API.cursor.execute(
                f"UPDATE note SET title = '{notename}', \
                                  contentfile = '{notecontent}' \
                  WHERE noteid = '{note}'")

            for tag in notetags:
                tagname = tag["tagname"]
                try:
                    API.cursor.execute(
                        f"INSERT INTO notetag(tagname, noteid) VALUES ('{tagname}', '{noteid}')"
                    )
                except (Exception, psycopg2.Error) as error1:
                    print('Error while executing to PostgreSQL', error1)
        except (Exception, psycopg2.Error) as error:
            print('Error while executing to PostgreSQL', error)
            API.cursor.rollback()
            return jsonify({"status": 0})
        API.connection.commit()
        return jsonify({"status": 1})

    if request.method == "DELETE":
        try:
            API.cursor.execute(f"DELETE FROM notetag WHERE noteid = '{note}'")
            API.cursor.execute(f"DELETE FROM noteinnotebook WHERE noteid = '{note}'")
            API.cursor.execute(f"DELETE FROM note WHERE noteid = '{note}'")
        except (Exception, psycopg2.Error) as error:
            print('Error while executing to PostgreSQL', error)
            API.cursor.rollback()
            return jsonify({"status": 0})
        API.connection.commit()
        return jsonify({"status": 1})
