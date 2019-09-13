import pickle
import numpy as np
from sklearn.svm import SVR
import matplotlib.pyplot as plt
import pandas as pd
from datetime import datetime, timedelta
import time

columns=['state','district','market','commodity','variety','date','min_price','max_price','modal_price']
df = pd.read_csv('mahaMain.csv',names=columns,skiprows=1)

date = [[(datetime(2019,m,1)-datetime(2019,1,1)).days] for m in range(1,13)]

for name,group in df.groupby('market'):
    print("#############################")
    print(name)
    print("#############################")
    X=pd.to_datetime(group.date,infer_datetime_format=2)
    y = group.modal_price
    x_ = (X-datetime(2019,1,1)).values.reshape(-1,1)
    y_ = y.values.reshape(-1,1)
    svr_rbf = SVR(kernel='rbf', C=1, gamma='auto',max_iter=500,verbose=1)
    y_rbf = svr_rbf.fit(x_,y_)
    print("name : {}, prediction for 8/8/19 : {}".format(name,y_rbf.predict(x_)))
    filename = "models/" + name + '.sav'
    pickle.dump(y_rbf, open(filename, 'wb'))

    loaded_model = pickle.load(open(filename, 'rb'))
    result = loaded_model.predict(x_)
    print(result)
    # plt.plot(date, svr_rbf.predict(date), c='r', label='RBF model')
    # plt.xlabel('Date')
    # plt.ylabel('Price')
    # plt.title('Support Vector Regression')
    # plt.legend()
    # plt.show()
