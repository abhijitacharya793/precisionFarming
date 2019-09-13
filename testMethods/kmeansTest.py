import matplotlib.pyplot as plt
from scipy import ndimage
from sklearn import cluster
import numpy as np

image = ndimage.imread("393.jpg")

x,y,z = image.shape

image_2d = image.reshape(x*y,z)

kmeans_cluster = cluster.KMeans(n_clusters = 6)
kmeans_cluster.fit(image_2d)
cluster_centers = kmeans_cluster.cluster_centers_
cluster_labels = kmeans_cluster.labels_

print(cluster_centers)
#for i in range(5):
#    cluster_centers[i]=[0,0,0]
image_kmeans = (cluster_centers[cluster_labels].reshape(x,y,z)).astype(np.uint8)
#######################################################

#######################################################
plt.figure()
plt.imshow(image_kmeans)
plt.show()
