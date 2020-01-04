import os

from flask import Flask
from flask_mail import Mail

from API.routes import account_api
from UploadDownloadAPI.routes import uploadDownloadAPI

app = Flask(__name__, template_folder='../Templates')

mail_settings = {
    "MAIL_SERVER": 'smtp.gmail.com',
    "MAIL_PORT": 465,
    "MAIL_USE_TLS": False,
    "MAIL_USE_SSL": True,
    "MAIL_USERNAME": os.environ['MAIL_USERNAME'],
    "MAIL_PASSWORD": os.environ['MAIL_PASSWORD']
}

app.config.update(mail_settings)

app.register_blueprint(account_api)
app.register_blueprint(uploadDownloadAPI)

mail = Mail(app)

from program import routes
