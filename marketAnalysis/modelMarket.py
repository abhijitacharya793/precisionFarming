import pickle
import numpy as np
from sklearn.svm import SVR
import matplotlib.pyplot as plt
import pandas as pd
from datetime import datetime, timedelta
import time

start=time.time()

columns=['state','district','market','commodity','variety','date','min_price','max_price','modal_price']#['date','centre_name','commodity_name','price']

df = pd.read_csv('mainCSV.csv',names=columns,skiprows=1)
# print(df.id)
for name,group in df.groupby('state'):
    X=pd.to_datetime(group.date,infer_datetime_format=2)
    # print(((X-datetime(2013,1,1)).values.reshape(-1,1)))
    # print(X)
    y = group.modal_price
    cc=(X).values.reshape(-1,1)
    print(cc)
    svr_lin = SVR(kernel='rbf', C=1, gamma='auto',max_iter=500,verbose=1)
    y_lin = svr_lin.fit(cc, y)
    # save the model to disk
    filename = group.state.values[0]+'.sav'
    pickle.dump(y_lin, open(filename, 'wb'))
    print(y_lin)

    #f.write("{},{}\n".format(name,y_lin))
    print("\n\n")
    print("#########################################################")
    print("\n\n")

# #############################################################################
print("Total Time Taken Is {}".format(time.time()-start))
