from flask import jsonify
from flask import request
from flask_mail import Message

from program import app
from program import mail


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


@app.route('/test/gmail', methods=["GET"])
def sendMail():
    with app.app_context():
        msg = Message(subject="Hello",
                      sender=app.config.get("MAIL_USERNAME"),
                      recipients=["lamducanhndgv@gmail.com"],  # replace with your email for testing
                      body="This is a test email I sent with Gmail and Python!")
        mail.send(msg)
        return "Successfully"
