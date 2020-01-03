import os
from flask import Blueprint, request, jsonify, render_template, send_from_directory, current_app, g
from werkzeug.utils import secure_filename

uploadDownloadAPI = Blueprint("uploadDownloadAPI", __name__)
WEB_URL = "https://e62fd3d2.ngrok.io"


@uploadDownloadAPI.route('/upload', methods=['GET'])
def upload_file_template():
    return render_template('API/uploadAPI.html')


@uploadDownloadAPI.route('/uploader/upload', methods=['GET', 'POST'])
def upload_file():
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
        return jsonify({'url': (WEB_URL + f'/download/{option}/' + f.filename)})


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
