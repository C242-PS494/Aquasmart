const tf = require('@tensorflow/tfjs-node');
require('dotenv').config();

const MODEL_PATH = process.env.MODEL2;

let model;

async function loadModel() {
    if (!model) {
        model = await tf.loadGraphModel(MODEL_PATH);
        console.log('Model berhasil dimuat');
    }
    return model;
}

module.exports = loadModel;