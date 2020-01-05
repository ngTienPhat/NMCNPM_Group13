from flask import jsonify
from flask import request
from flask_mail import Message

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


@app.route('/test/gmail', methods=["GET"])
def sendMail():
    with app.app_context():
        msg = Message(subject="[EVERNOTE] RESET PASSWORD",
                      sender=app.config.get("MAIL_USERNAME"),
                      recipients=["lamducanhndgv@gmail.com"],  # replace with your email for testing
                      body="This is a test email I sent with Gmail and Python!")
        mail.send(msg)
        return "Successfully"
