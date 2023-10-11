import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder, StandardScaler
from tensorflow.keras.models import model_from_json
import scipy.stats as stats

# Load the model architecture
with open('models/model_architecture.json', 'r') as json_file:
    loaded_model_json = json_file.read()
    loaded_model = model_from_json(loaded_model_json)

# Load the saved weights
loaded_model.load_weights('models/BioBeaconMLModel.h5')

# Load your external data (replace 'external_data.csv' with your file)
external_data = pd.read_csv('data/temp.csv')

# Preprocess the external data (similar to your training data preprocessing)

# Have columns 'x1' to 'z5'
X_external = external_data[['x1', 'y1', 'z1', 'x2', 'y2', 'z2', 'x3', 'y3', 'z3', 'x4', 'y4', 'z4', 'x5', 'y5', 'z5']]

X_external['x1'] = X_external['x1'].astype('float')
X_external['y1'] = X_external['y1'].astype('float')
X_external['z1'] = X_external['z1'].astype('float')
X_external['x2'] = X_external['x2'].astype('float')
X_external['y2'] = X_external['y2'].astype('float')
X_external['z2'] = X_external['z2'].astype('float')
X_external['x3'] = X_external['x3'].astype('float')
X_external['y3'] = X_external['y3'].astype('float')
X_external['z3'] = X_external['z3'].astype('float')
X_external['x4'] = X_external['x4'].astype('float')
X_external['y4'] = X_external['y4'].astype('float')
X_external['z4'] = X_external['z4'].astype('float')
X_external['x5'] = X_external['x5'].astype('float')
X_external['y5'] = X_external['y5'].astype('float')
X_external['z5'] = X_external['z5'].astype('float')


# Load the scaler used during training
scaler = StandardScaler()
scaler.mean_ = np.load('scalars/scaler_mean.npy')  # Load the mean from the saved file
scaler.scale_ = np.load('scalars/scaler_std.npy')   # Load the standard deviation from the saved file

# Perform feature scaling using the loaded scaler
X_external_scaled = scaler.transform(X_external)

scaled_X = pd.DataFrame(data = X_external_scaled, columns = ['x1', 'y1', 'z1', 'x2', 'y2', 'z2', 'x3', 'y3', 'z3', 'x4', 'y4', 'z4', 'x5', 'y5', 'z5'])


# Function to create frames from the external data
def get_frames(df, frame_size, hop_size):
    N_FEATURES = 15
    frames = []
    labels = []
    for i in range(0, len(df) - frame_size, hop_size):
        x1 = df['x1'].values[i: i + frame_size]
        y1 = df['y1'].values[i: i + frame_size]
        z1 = df['z1'].values[i: i + frame_size]
        x2 = df['x2'].values[i: i + frame_size]
        y2 = df['y2'].values[i: i + frame_size]
        z2 = df['z2'].values[i: i + frame_size]
        x3 = df['x3'].values[i: i + frame_size]
        y3 = df['y3'].values[i: i + frame_size]
        z3 = df['z3'].values[i: i + frame_size]
        x4 = df['x4'].values[i: i + frame_size]
        y4 = df['y4'].values[i: i + frame_size]
        z4 = df['z4'].values[i: i + frame_size]
        x5 = df['x5'].values[i: i + frame_size]
        y5 = df['y5'].values[i: i + frame_size]
        z5 = df['z5'].values[i: i + frame_size]

        # Retrieve the most often used label in this segment
        frames.append([x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5])

    # Bring the segments into a better shape
    frames = np.asarray(frames).reshape(-1, frame_size, N_FEATURES)

    return frames

# Set frame size and hop size
frame_size = 400
hop_size = 400

# Get frames and labels from the external data
X= get_frames(scaled_X, frame_size, hop_size)

# Make predictions for each frame
predictions = loaded_model.predict(X)
predicted_external_labels = np.argmax(predictions, axis=1)

# In 'predicted_labels', you'll have the predicted labels for your external data frames
print(predicted_external_labels)
predicted_external_labels