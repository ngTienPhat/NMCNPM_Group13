import os

import psycopg2
from flask import Blueprint, request, jsonify, render_template, send_from_directory, current_app
from werkzeug.utils import secure_filename

import API

uploadDownloadAPI = Blueprint("uploadDownloadAPI", __name__)
WEB_URL = "https://project-nmcnpm.herokuapp.com"


@uploadDownloadAPI.route('/upload', methods=['GET'])
def upload_file_template():
    return render_template('API/uploadAPI.html')


@uploadDownloadAPI.route('/uploader/upload', methods=['GET', 'POST'])
@uploadDownloadAPI.route('/uploader/upload/<userid>', methods=['GET', 'POST'])
def upload_file(userid=None):
    if request.method in ["POST", "GET"]:
        f = request.files['file']
        s = f.filename.lower()
        pathreturn = None
        option = None
        print(s)
        if s.split('.')[-1] in ['png', 'jpeg', 'jpg', 'bmp']:
            option = 'image'
            pathreturn = os.path.join(f'media\\images', secure_filename(f.filename))
        elif s.split('.')[-1] == 'json':
            option = 'json'
            pathreturn = os.path.join(f'media\\json', secure_filename(f.filename))
        else:
            return jsonify({"status": 0})
        f.save(pathreturn)
        result_URL = WEB_URL + f'/download/{option}/' + f.filename
        if userid:
            try:
                API.cursor.execute(f"UPDATE userinfo SET avatar = '{result_URL}' \
                                     WHERE userid = '{userid}'")
            except (Exception, psycopg2.Error) as error:
                print('Error while executing to PostgreSQL', error)
                API.cursor.rollback()
                return jsonify({"status": 0})
            API.connection.commit()
        return jsonify({'url': result_URL})
    return jsonify({"status": 0})


@uploadDownloadAPI.route('/download/<option>/<path:filename>', methods=['GET'])
def downloadImg(option, filename):
    downloads = None
    if option == 'image':
        downloads = os.path.join(current_app.root_path, '..\\media\\images')
    elif option == 'json':
        downloads = os.path.join(current_app.root_path, '..\\media\\json')
    else:
        return jsonify({"status": 0})
    return send_from_directory(directory=downloads, filename=filename)
