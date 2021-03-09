import smbus2
import bme280
import io
import time
import base64
import atexit
import logging
from flask import Flask
from picamera import PiCamera
from flask import request, Response
from requests_toolbelt import MultipartEncoder
from flask import send_file, make_response
from werkzeug import FileWrapper
import sys



APP_KEY = 'r[8ikCroO!z3B$S^xkszT'

app = Flask(__name__)
log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)
camera = PiCamera()
camera.resolution = (1280, 720)

def OnExitApp():
    camera.close()
atexit.register(OnExitApp)

def capture_camera_preview_as_base64():
    image_stream = io.BytesIO()
    camera.capture(image_stream, 'jpeg', quality=30)
    image_stream.seek(0)
    return send_file(image_stream, mimetype='image/jpeg', as_attachment=True, attachment_filename='%s.jpg' % 'preview')


@app.route('/cameraPreview')
def getCameraPreviewAsBase64():
    key = request.args.get('key', default = '', type = str)
    if key == APP_KEY:
        return capture_camera_preview_as_base64()
    else:
        return "Incorrect APP KEY", 401

if __name__ == '__main__':
    app.run(host= '0.0.0.0')







