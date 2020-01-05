import hashlib
import uuid

import psycopg2
from flask import jsonify
from flask import request
from flask_mail import Message

import API
from program import app
from program import mail


@app.route('/')
def main_page():
    return "<h1>Hello world</h1>"


@app.route('/test')
def test_page(teststring=None):
    if teststring:
        print(teststring)
    else:
        print(request.form)
    return jsonify([{"a": 1, "b": [{"c": 3, "d": 4}, {"c": 5, "d": 6}]}])


@app.route('/test/uploadJSON', methods=["POST"])
def test_json():
    print(request.json)
    contentJSON = request.json
    for key, value in contentJSON.items():
        if value:
            print(value)
    return jsonify({'status': 1})


@app.route('/users/forgotPassword', methods=["POST"])
def forgotPass():
    contentJSON = request.json
    username = contentJSON.get('username', None)
    useremail = contentJSON.get('useremail', None)
    if useremail and username:
        API.cursor.execute(f"SELECT * FROM userinfo WHERE useremail = '{useremail}' AND username = '{username}'")
        if len(API.cursor.fetchone()) < 1:
            return jsonify({"status": 0})

        useremail = useremail.strip()
        newpassword = uuid.uuid1().hex[:12]
        hashpassword = hashlib.md5(newpassword.encode()).hexdigest()
        try:
            API.cursor.execute(f"UPDATE userinfo \
                                 SET userpassword = '{hashpassword}' \
                                 WHERE useremail = '{useremail}' AND username = '{username}'")
        except (Exception, psycopg2.Error) as error:
            print('Error while executing to PostgreSQL', error)
            API.connection.rollback()
            return jsonify({"status": 0})
        with app.app_context():
            msg = Message(subject="[EVERNOTE] RESET PASSWORD",
                          sender=app.config.get("MAIL_USERNAME"),
                          recipients=[useremail],  # replace with your email for testing
                          body=("This is a new password for your account: " + newpassword))
            mail.send(msg)
        API.connection.commit()
        return jsonify({"status": 1})

    return jsonify({"status": 0})
