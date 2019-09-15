from keras.preprocessing.image import ImageDataGenerator,img_to_array,load_img
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D
from keras.layers import Activation, Dropout, Flatten, Dense
from keras import backend as K
import cv2
import numpy as np

# dimensions of our images.
img_width, img_height = 300, 300

train_data_dir = '/home/katsuro/Documents/repo/precisionFarming/trainkmeansdata/train'
validation_data_dir = '/home/katsuro/Documents/repo/precisionFarming/trainkmeansdata/validation'
nb_train_samples = 696
nb_validation_samples = 264
epochs = 30
batch_size = 16

if K.image_data_format() == 'channels_first':
    input_shape = (3, img_width, img_height)
else:
    input_shape = (img_width, img_height, 3)

model = Sequential()
model.add(Conv2D(32, (3, 3), input_shape=input_shape))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(32, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(64, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Flatten())
model.add(Dense(64))
model.add(Activation('relu'))
model.add(Dropout(0.5))
model.add(Dense(1))
model.add(Activation('sigmoid'))
model.load_weights('/home/katsuro/Documents/repo/precisionFarming/trainkmeansdata/s1_kmeans.h5')

model.compile(loss='binary_crossentropy',
              optimizer='rmsprop',
              metrics=['accuracy'])


model.summary()

img = load_img('/home/katsuro/Documents/repo/precisionFarming/trainkmeansdata/train/blast/10.jpg', target_size=(img_width, img_height))
x = img_to_array(img)
x = np.expand_dims(x, axis=0)
img = load_img('/home/katsuro/Documents/repo/precisionFarming/trainkmeansdata/train/blight/13.jpg', target_size=(img_width, img_height))
y = img_to_array(img)
y = np.expand_dims(y, axis=0)
img = load_img('/home/katsuro/Documents/repo/precisionFarming/trainkmeansdata/train/brownspot/412.jpg', target_size=(img_width, img_height))
z = img_to_array(img)
z = np.expand_dims(z, axis=0)
images = np.vstack([x,y,z])
classes = model.predict_classes(images, batch_size=10)
print(classes)
