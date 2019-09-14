from __future__ import print_function
import keras
from keras.datasets import cifar10
from keras.preprocessing.image import ImageDataGenerator
from keras.models import Sequential,load_model
from keras.layers import Dense, Dropout, Activation, Flatten
from keras.layers import Conv2D, MaxPooling2D
import os
import matplotlib.pyplot as plt

model = load_model('saved_models/keras_cifar10_trained_model.h5')

model.summary()

(x_train, y_train), (x_test, y_test) = cifar10.load_data()

plt.imshow(x_train[0,:,:,:], interpolation='nearest')
plt.show()
print(y_train[0])
# x_train has 50000 frames with each frame having some rows and columns, each element is an rgb vector
# print("##########################")
# print(y_train)
# print("##########################")
# print(x_test)
# print("##########################")
# print(y_test)
# print("##########################")
# print('x_train shape:', x_train.shape)
# print(x_train.shape[0], 'train samples')
# print(x_test.shape[0], 'test samples')
# print("##########################")
