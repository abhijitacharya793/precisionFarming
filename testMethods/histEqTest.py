import numpy as np
import cv2
import matplotlib.pyplot as plt

img = cv2.imread('392.jpg',0)

# create a CLAHE object (Arguments are optional).
clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
cl1 = clahe.apply(img)

#plt.imshow(cl1)
#plt.show()
print(cl1.shape)

cv2.imshow('clahe_2.jpg',cl1)
cv2.waitKey(0)
cv2.destroyAllWindows()
