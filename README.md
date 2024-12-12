
# Fitur Water Quality

This contains information about the endpoint, the model, and the steps to deploy using Google Compute Engine.


## How To Deploy To Google Compute Engine
## Deployment

Create Instance

```bash
 gcloud compute instances create [INSTANCE_NAME] \
    --zone=[ZONE] \
    --machine-type=[MACHINE_TYPE] \
    --image-family=[IMAGE_FAMILY] \
    --image-project=[IMAGE_PROJECT] \
    --boot-disk-size=[DISK_SIZE] \
    --tags=[TAG1,TAG2]

```
Establish an SSH connection between your local computer and a Virtual Machine (VM) on Google Cloud
```bash
  ssh -i ~/.ssh/[KEY_NAME] [USERNAME]@[VM_EXTERNAL_IP]

```
Install nvm

```bash
  curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
```
Install npm

```bash
  nvm install 18
```

Clone the project

```bash
  git clone -b dev-fitur2-rafi https://github.com/C242-PS494/Aquasmart.git
```

Go to the project directory

```bash
  cd model2
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```


    
# Endpoint

## Post water quality

- URL
```http
  POST /api/water-quality
```
- URL Params

  Required: language = `[string]` 'indonesia'


- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Body Request:
```http
{
  "ph": 10.0,
  "turbidity": 1.5,
  "temperature" : 30
}
```

- Success Response:
  
  Code: 200
```http
  {
    "message": "Data berhasil ditambahkan!",
    "water_quality_id": 39,
    "prediction": "Buruk",
    "recommendation": "Hasil prediksi: Buruk. Kondisi lingkungan sangat buruk. Berikut langkah perbaikan yang direkomendasikan:- pH (10) berada di luar batas ideal (6.5 - 8.5). Periksa dan sesuaikan pH air menggunakan penambahan asam/basa sesuai kebutuhan.- Kekeruhan air (1.5) sudah pada tingkat yang aman.- Suhu (30°C) sudah berada dalam kisaran optimal."
    
}
```

- Error Response:

  Code: 500
```http
 {
    "message": "Error saat memproses prediksi ML",
    "error": "error.response.data"
}

```

```http
 {
    "message": "Database error atau masalah koneksi!",
    "error": "error.message"
}

```

## Get Prediksi Air

- URL
```http
  GET /api/water-quality/predictions
```
- URL Params

  Required: language = `[string]` 'indonesia'


- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Response:
  
  Code: 200
```http
 {
     {
        "prediction_id": 3,
        "water_quality_id": 3,
        "prediction": "Buruk",
        "recommendation": "Hasil prediksi: Buruk. Kondisi lingkungan sangat buruk. Berikut langkah perbaikan yang direkomendasikan:- pH (10) berada di luar batas ideal (6.5 - 8.5). Periksa dan sesuaikan pH air menggunakan penambahan asam/basa sesuai kebutuhan.- Kekeruhan air (1.5) sudah pada tingkat yang aman.- Suhu (30°C) sudah berada dalam kisaran optimal.",
        "ph": 10,
        "turbidity": 1.5,
        "temperature": 30,
        "created_at": "2024-12-12T07:31:40.000Z",
        "user_id": "5Yt7E_brh5-SjI7c"
    },
    {
        "prediction_id": 39,
        "water_quality_id": 39,
        "prediction": "Buruk",
        "recommendation": "Hasil prediksi: Buruk. Kondisi lingkungan sangat buruk. Berikut langkah perbaikan yang direkomendasikan:- pH (10) berada di luar batas ideal (6.5 - 8.5). Periksa dan sesuaikan pH air menggunakan penambahan asam/basa sesuai kebutuhan.- Kekeruhan air (1.5) sudah pada tingkat yang aman.- Suhu (30°C) sudah berada dalam kisaran optimal.",
        "ph": 10,
        "turbidity": 1.5,
        "temperature": 30,
        "created_at": "2024-12-12T17:32:16.000Z",
        "user_id": "5Yt7E_brh5-SjI7c"
    }
}

```
  - Error Response:

  Code: 401
```http
 {
    "status": "fail",
    "message": "Token tidak valid atau sudah kadaluarsa."
}

```

Code: 500
```http
 {
    "message": "Database error atau masalah koneksi!",
    "error": "error.message"
}

```

## Get Water Quality

- URL
```http
  GET /api/water-quality
```
- URL Params

  Required: language = `[string]` 'indonesia'


- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Response:
  
  Code: 200
```http
 {
    [
    {
        "id": 3,
        "ph": 10,
        "turbidity": 1.5,
        "temperature": 30,
        "user_id": "5Yt7E_brh5-SjI7c",
        "created_at": "2024-12-12T07:31:40.000Z"
    },
    {
        "id": 39,
        "ph": 10,
        "turbidity": 1.5,
        "temperature": 30,
        "user_id": "5Yt7E_brh5-SjI7c",
        "created_at": "2024-12-12T17:32:16.000Z"
    }
]
}

```
  - Error Response:

  Code: 401
```http
 {
    "status": "fail",
    "message": "Token tidak valid atau sudah kadaluarsa."
}

```

Code: 500
```http
 {
    "message": "Database error atau masalah koneksi!",
    "error": "error.message"
}

```
```

## Delete water quality

- URL
```http
  DELETE /api/water-quality/{id}
```

- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Response:
  
  Code: 200
```http
{
    "message": "Data berhasil dihapus!"
}

```
  
- Error Response:

  Code: 404
```http
 {
    "message": "Data tidak ditemukan!",
}
```

Code: 500
```http
 {
    "message": "Database error!",
    "error": error.message
}
```
