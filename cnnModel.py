import os 
import glob
from keras.layers import Dense
from keras.models import Sequential
from keras.preprocessing import image
import numpy as np
from keras.models import load_model
from keras.preprocessing import image
from keras.callbacks import ReduceLROnPlateau
from keras.preprocessing.image import ImageDataGenerator
from keras.layers import Dense,Dropout,Flatten
from keras.layers import Conv2D, MaxPooling2D


def get_files(directory):
  if not os.path.exists(directory):
    return 0
  count=0
  # crawls inside folders
  for current_path,dirs,files in os.walk(directory):
    for dr in dirs:
      count+= len(glob.glob(os.path.join(current_path,dr+"/*")))
  return count
train_dir ="C:/Users/Likith/OneDrive/Desktop/plantAIML/PlantVillage-Dataset/raw/color"

 #train file image count
train_samples =get_files(train_dir)
#to get tags
num_classes=len(glob.glob(train_dir+"/*")) 
#test file image count


train_datagen=ImageDataGenerator(
    rescale=1./255,
    shear_range=0.2,
    zoom_range=0.2,
    horizontal_flip=True,
    validation_split=0.2
    )
test_datagen=ImageDataGenerator(rescale=1./255)


input_shape=(256,256,3)
train_generator = train_datagen.flow_from_directory(
  train_dir,
  target_size=(256,256),
  batch_size=32,
  subset='training')
test_generator=train_datagen.flow_from_directory(
  train_dir,
  shuffle=True,
  target_size=(256,256),
  batch_size=32,
  subset='validation'
  )
#xTest = test_datagen.flow_from_directory("C:/Users/Likith/OneDrive/Desktop/plantAIML/PlantVillage-Dataset/raw/color/Tomato___healthy/6cabb19a-2a35-4897-a646-a13e3cf494c6___RS_HL 0591.JPG", shuffle= False, target_size=(256,256), batch_size=1)
classes=list(train_generator.class_indices.keys())

def train():
  model = Sequential()
  model.add(Conv2D(32, (5, 5),input_shape=input_shape,activation='relu',name="conv2d_1"))
  model.add(MaxPooling2D(pool_size=(3, 3),name="max_pooling2d_1"))
  model.add(Conv2D(32, (3, 3),activation='relu',name="conv2d_2"))
  model.add(MaxPooling2D(pool_size=(2, 2),name="max_pooling2d_2"))
  model.add(Conv2D(64, (3, 3),activation='relu',name="conv2d_3"))
  model.add(MaxPooling2D(pool_size=(2, 2),name="max_pooling2d_3"))   
  model.add(Flatten(name="flatten_1"))
  model.add(Dense(512,activation='relu'))
  model.add(Dropout(0.25))
  model.add(Dense(128,activation='relu'))          
  model.add(Dense(num_classes,activation='softmax'))
  model.summary()


  validation_generator = train_datagen.flow_from_directory(
                         train_dir,
                         shuffle=True,
                         target_size=(256,256),
                         batch_size=32,
                         subset='validation'
                         )

  model.compile(optimizer='adam',loss = 'categorical_crossentropy',metrics=['accuracy'])
  history1 = model.fit(
      train_generator,#egitim verileri
      steps_per_epoch=None,
      epochs=2,
      validation_data=validation_generator,
      validation_steps=None,
      verbose=1,
      callbacks=[ReduceLROnPlateau(monitor='val_loss', factor=0.3,patience=3, min_lr=0.000001)],
      shuffle=True
      )
  model.save('./model.h5')

train()

 

model = load_model('./model.h5')


classes=list(train_generator.class_indices.keys())
# Pre-Processing test data same as train data.
def prepare(img_path):
    img = image.load_img(img_path, target_size=(256,256))
    x = image.img_to_array(img)
    x = x/255
    return np.expand_dims(x, axis=0)
    
img_url="C:/Users/Likith/OneDrive/Desktop/plantAIML/PlantVillage-Dataset/raw/color/Tomato___healthy/6cabb19a-2a35-4897-a646-a13e3cf494c6___RS_HL 0591.JPG"
result_cnn = model.predict([prepare(img_url)])

classresult=np.argmax(result_cnn,axis=1)
print(classes[classresult[0]])