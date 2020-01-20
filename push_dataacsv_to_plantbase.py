from googletrans import Translator
import pandas as pd

translator = Translator()
df = pd.read_csv("data.csv")

translator.translate('apple',dest='hi').text
df['Disease Name'][0]
