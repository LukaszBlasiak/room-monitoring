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
import json



APP_KEY = 'r[8ikCroO!z3B$S^xkszT'

app = Flask(__name__)
log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)
camera = PiCamera()
camera.resolution = (1280, 720)

cracow_altitude = 257
port = 1
address = 0x76
bus = smbus2.SMBus(port)
calibration_params = bme280.load_calibration_params(bus, address)

def OnExitApp():
    camera.close()
atexit.register(OnExitApp)

def get_camera_preview_as_base64():
    image_stream = io.BytesIO()
    camera.capture(image_stream, 'jpeg', quality=30)
    image_stream.seek(0)
    return send_file(image_stream, mimetype='image/jpeg', as_attachment=True, attachment_filename='%s.jpg' % 'preview')


@app.route('/cameraPreview')
def get_camera_preview_as_base64_response():
    key = request.args.get('key', default = '', type = str)
    if key == APP_KEY:
        return get_camera_preview_as_base64()
    else:
        return "Incorrect APP KEY", 401

def get_bme280_measurements():
    data = bme280.sample(bus, address, calibration_params)
    response_model = {}
    response_model["temperature"] = data.temperature
    response_model["humidity"] = data.humidity
    response_model["pressure"] = data.pressure + cracow_altitude/8.3
    return json.dumps(response_model)

@app.route('/bme280')
def get_bme280_measurements_response():
    key = request.args.get('key', default = '', type = str)
    if key == APP_KEY:
        response_model = get_bme280_measurements()
        return Response(response=response_model,
                        status=200,
                        mimetype="application/json")
    else:
        return "Incorrect APP KEY", 401

if __name__ == '__main__':
    app.run(host= '0.0.0.0')








