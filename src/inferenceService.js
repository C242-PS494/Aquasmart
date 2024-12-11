const tf = require('@tensorflow/tfjs-node');

// Scaler Parameters
const scalerParams = {
    mean: { Fish_Size: 27.28923169, Feed_Amount: 2.76832248 },
    std: { Fish_Size: 13.167002, Feed_Amount: 1.30048413 }
};

// Mapping Kategorikal
const waterConditionMap = { 'baik': 0, 'buruk': 1, 'sedang': 2 };
const fishTypeMap = {
    'Bandeng': 0, 'Bawal': 1, 'Gabus': 2, 'Gurami': 3, 'Ikan Mas': 4,
    'Kakap': 5, 'Lele': 6, 'Mujair': 7, 'Nila': 8, 'Tenggiri': 9
};

// Fungsi untuk normalisasi input
function normalizeInput(fish_size, feed_amount) {
    const normalizedFishSize = (fish_size - scalerParams.mean.Fish_Size) / scalerParams.std.Fish_Size;
    const normalizedFeedAmount = (feed_amount - scalerParams.mean.Feed_Amount) / scalerParams.std.Feed_Amount;
    return [normalizedFishSize, normalizedFeedAmount];
}

// Fungsi prediksi
async function predictHarvestAndFeed(model, fish_size, water_condition, fish_type, feed_amount) {
    try {
        // Encode nilai kategorikal
        const encodedCondition = waterConditionMap[water_condition] ?? 0;
        const encodedFishType = fishTypeMap[fish_type] ?? 0;

        // Normalisasi nilai numerik
        const [normalizedFishSize, normalizedFeedAmount] = normalizeInput(fish_size, feed_amount);

        // Buat tensor input
        const inputTensor = tf.tensor2d([[normalizedFishSize, encodedCondition, encodedFishType, normalizedFeedAmount]], [1, 4]);

        // Prediksi dengan model
        const predictions = model.execute({ 'keras_tensor:0': inputTensor }, ['Identity:0', 'Identity_1:0']);

        // Ambil data prediksi
        const predictedDaysToHarvest = Math.round(predictions[0].arraySync()[0][0]);
        const recommendedFeed = parseFloat(predictions[1].arraySync()[0][0].toFixed(2));

        return {
            predicted_days_to_harvest: predictedDaysToHarvest,
            recommended_feed: recommendedFeed
        };
    } catch (error) {
        console.error('Gagal melakukan prediksi:', error);
        throw error;
    }
}

module.exports = { predictHarvestAndFeed };
