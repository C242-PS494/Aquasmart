const tf = require('@tensorflow/tfjs-node');

const MODEL_PATH = './models/model.json';

let model;

async function loadModel() {
    if (!model) {
        model = await tf.loadGraphModel(`file://${MODEL_PATH}`);
        console.log('Model berhasil dimuat');
    }
    return model;
}

module.exports = loadModel;