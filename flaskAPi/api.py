# Observatory Service

# Import framework
from flask import Flask, jsonify
from flask_restful import Resource, Api
import os
from flask import Flask, jsonify, request, render_template, redirect
from keras.models import load_model
import keras.utils as image
import numpy as np
import cv2
from PIL import Image
import random
from tqdm.notebook import tqdm
from flask_cors import CORS

# Instantiate the app
app = Flask(__name__)
CORS(app)
api = Api(app)
app.config["IMAGE_UPLOADS"] = "."
model = load_model('/usr/src/app/model_VGG16.h5')


@app.route("/upload-image", methods=["GET","POST"])
def upload_image():
    if request.method == "POST":
        if request.files:
            image = request.files["image"]
            image.save(os.path.join(app.config["IMAGE_UPLOADS"], "image.jpeg"))
            img0 = "image.jpeg"
            image = Image.open(img0)
            new_image = image.resize((224,224))
            classes = ['Apple Apple scab', 'Apple Black rot', 'Apple Cedar apple rust', 'Apple healthy', 'Blueberry healthy', 'Cherry(including sour) Powdery mildew', 'Cherry (including sour) healthy', 'Corn(maize) Cercospora leaf spot Gray leaf spot', 'Corn(maize) Common rust', 'Corn(maize) Northern Leaf Blight', 'Corn(maize) healthy', 'Grape Black rot', 'Grape Esca(Black Measles)', 'Grape Leaf blight(Isariopsis Leaf Spot)', 'Grape healthy', 'Orange Haunglongbing(Citrus greening)', 'Peach Bacterial spot', 'Peach healthy', 'Bellpepper Bacterial spot', 'Bellpepper healthy', 'Potato Early blight', 'Potato Late blight', 'Potato healthy', 'Raspberry healthy','Soybean healthy', 'Squash Powdery mildew', 'Strawberry Leaf scorch', 'Strawberry healthy', 'Tomato Bacterial spot', 'Tomato Early blight', 'Tomato Late blight', 'Tomato Leaf Mold', 'Tomato Septoria leaf spot', 'Tomato Spider mites Two-spotted spider mite', 'Tomato Target Spot', 'Tomato Yellow Leaf Curl Virus', 'Tomato mosaic virus', 'Tomato healthy']
            img_arr = np.asarray(new_image)
            img_arr = img_arr/223
            result_cnn = model.predict(np.expand_dims(img_arr, axis=0))
            classresult=np.argmax(result_cnn,axis=1)
            message = {"Species": classes[classresult[0]]}
            response = jsonify(message)
            response.headers.add("Access-Control-Allow-Origin", "*")
            return(response)
    return render_template("upload_image.html")
    

# Run the application
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80, debug=True)
 
