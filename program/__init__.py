from flask import Flask, g
from API.routes import account_api
from UploadDownloadAPI.routes import uploadDownloadAPI

app = Flask(__name__, template_folder='../Templates')

app.register_blueprint(account_api)
app.register_blueprint(uploadDownloadAPI)

from program import routes
