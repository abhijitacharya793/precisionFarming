import numpy as np
import cv2
import matplotlib.pyplot as plt
import numpy as np
from scipy import ndimage
from sklearn import cluster
from PIL import Image, ImageTk
from mpl_toolkits.mplot3d import Axes3D

# im = int(input("Enter the image"))
img = cv2.imread('392.jpg')#.format(im))
imgOrig = img.copy()
img = cv2.resize(img,(300,300))
blur = cv2.GaussianBlur(img,(5,5),0)

# create a CLAHE object (Arguments are optional).
clahe = cv2.createCLAHE(clipLimit=5, tileGridSize=(8,8))
# cl1 = np.zeros([300,300,3])
# cl1[:,:,0] = clahe.apply(blur[:,:,0])
# cl1[:,:,1] = clahe.apply(blur[:,:,1])
# cl1[:,:,2] = clahe.apply(blur[:,:,2])
image_yuv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
image_yuv[:, :, 0] = clahe.apply(image_yuv[:, :, 0])
cl1 = cv2.cvtColor(image_yuv, cv2.COLOR_HSV2RGB)

cl1 = np.array(cl1)
cl1 = cl1.astype(int)
# ret, thresh = cv2.threshold(cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY), 80, 1, cv2.THRESH_BINARY)
# print(thresh)
# cl1[:,:,0] = cl1[:,:,0]*thresh
# cl1[:,:,1] = cl1[:,:,1]*thresh
# cl1[:,:,2] = cl1[:,:,2]*thresh
img = cl1
img1 = cl1
x,y,z = img.shape

image_2d = img.reshape(x*y,z)

kmeans_cluster = cluster.KMeans(n_clusters = 3,verbose=0)
kmeans_cluster.fit(image_2d)
cluster_centers = kmeans_cluster.cluster_centers_
cluster_labels = kmeans_cluster.labels_

image_kmeans = (cluster_centers[cluster_labels].reshape(x,y,z)).astype(np.uint8)
print(cluster_centers)
c1 = cluster_centers.copy()
c1[0] = [1,1,1]
c1[1] = [0,0,0]
c1[2] = [0,0,0]
image_c1 = (c1[cluster_labels].reshape(x,y,z)).astype(np.uint8)
c2 = cluster_centers.copy()
c2[0] = [0,0,0]
c2[1] = [1,1,1]
c2[2] = [0,0,0]
image_c2 = (c2[cluster_labels].reshape(x,y,z)).astype(np.uint8)
c3 = cluster_centers.copy()
c3[0] = [0,0,0]
c3[1] = [0,0,0]
c3[2] = [1,1,1]
image_c3 = (c3[cluster_labels].reshape(x,y,z)).astype(np.uint8)
print(cluster_centers)
print(c1)
print(c2)
print(c3)
print(image_c1)

plt.subplot(2,2,1)
plt.imshow(imgOrig)
plt.subplot(2,2,2)
plt.imshow(image_c1*imgOrig)
plt.subplot(2,2,3)
plt.imshow(image_c2*imgOrig)
plt.subplot(2,2,4)
plt.imshow(image_c3*imgOrig)

plt.show()
# cv2.imshow("image",np.concatenate((image_kmeans,image_c1,image_c2,image_c3)).astype(np.uint8))
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imshow("image",(thresh*255).astype(np.uint8))
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imshow("image",cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY).astype(np.uint8))
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imshow("image",img1.astype(np.uint8))
# cv2.waitKey(0)
# cv2.destroyAllWindows()
