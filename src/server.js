const Hapi = require('@hapi/hapi');
const loadModel = require('./loadModel');
const { predictHarvestAndFeed } = require('./inferenceService');

let model;

// Fungsi untuk memulai server
async function startServer() {
    const server = Hapi.server({
        port: 3000,
        host: 'localhost'
    });

    // Endpoint untuk prediksi
    server.route({
        method: 'POST',
        path: '/predict',
        handler: async (request, h) => {
            const { fish_size, water_condition, fish_type, feed_amount } = request.payload;

            // Validasi input
            if (
                typeof fish_size !== 'number' ||
                typeof feed_amount !== 'number' ||
                typeof water_condition !== 'string' ||
                typeof fish_type !== 'string'
            ) {
                return h.response({ error: 'Format input tidak valid' }).code(400);
            }

            try {
                const result = await predictHarvestAndFeed(
                    model,
                    fish_size,
                    water_condition,
                    fish_type,
                    feed_amount
                );
                return result;
            } catch (error) {
                return h.response({ error: 'Terjadi kesalahan saat prediksi' }).code(500);
            }
        }
    });

    // Muat model sebelum memulai server
    model = await loadModel();

    // Jalankan server
    await server.start();
    console.log(`Server berjalan pada ${server.info.uri}`);
}

// Jalankan server
startServer().catch((err) => {
    console.error('Gagal memulai server:', err);
    process.exit(1);
});
