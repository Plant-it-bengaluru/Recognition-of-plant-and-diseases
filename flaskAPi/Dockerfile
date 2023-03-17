# https://hub.docker.com/r/library/python/tags/
FROM python:3.10.9

RUN pip install --no-cache-dir tensorflow
RUN pip install --no-cache-dir keras
RUN pip install --no-cache-dir Flask
RUN pip install --no-cache-dir flask-restful
RUN pip install --no-cache-dir pillow
RUN pip install --no-cache-dir numpy
RUN pip install --no-cache-dir tqdm
RUN pip install --no-cache-dir opencv-python
RUN pip install --no-cache-dir flask_cors
RUN apt-get update && apt-get install ffmpeg libsm6 libxext6  -y
RUN pip install --no-cache-dir ipywidgets --user
# Coping source in current directory into the image
# python:3-onbuild expects the source in /usr/src/app
COPY . /usr/src/app

# Commands in a list
CMD ["python", "/usr/src/app/api.py"]
