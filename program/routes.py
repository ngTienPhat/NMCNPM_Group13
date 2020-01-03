import os

from flask import render_template, jsonify
from flask import request
from werkzeug.utils import secure_filename

from program import app


@app.route('/')
def main_page():
    return "<h1>Hello world</h1>"


@app.route('/test')
@app.route('/test/<teststring>')
def test_page(teststring=None):
    if teststring:
        print(teststring)
    else:
        print(request.form)
    return jsonify([{"a": 1, "b": [{"c": 3, "d": 4}, {"c": 5, "d": 6}]}])


@app.route('/test/uploadJSON', methods=["POST"])
def test_json():
    print(request.json)
    print(type(request.json))
    return jsonify({'status': 1})
