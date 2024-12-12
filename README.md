
# Fitur Prediksi Panen Ikan

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
  git clone -b dev-fitur2-widda https://github.com/C242-PS494/Aquasmart.git
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

## Get history prediksi panen ikan

- URL
```http
  GET /prediksiPanen
```
- URL Params

  Required: language = `[string]` 'indonesia'


- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Respone:
  
  Code: 200
```http
  {
    "message": "Data prediksi panen berhasil diambil",
    "data": [
        {
            "id": 1,
            "user_id": "5Yt7E_brh5-SjI7c",
            "fish_size": "35",
            "water_condition": "buruk",
            "fish_type": "Nila",
            "feed_amount": 3.5,
            "predicted_days_to_harvest": 111,
            "recommended_feed": 2.92,
            "created_at": "2024-12-12T05:38:36.000Z",
            "updated_at": "2024-12-12T05:38:36.000Z"
        }
    ]
}
```

- Error Respone:

  Code: 500
```http
 {
    "message": 'Gagal mengambil data prediksi panen',
    "error": error.message
}

```

## Post prediksi panen ikan

- URL
```http
  POST /prediksiPanen
```
- URL Params

  Required: language = `[string]` 'indonesia'

- Body Request:
```http
  {
    "fish_size": 35,
    "water_condition": "buruk",
    "fish_type": "Nila",
    "feed_amount": 3.5
}
```

- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Respone:
  
  Code: 200
```http
 {
    "message": "Prediksi panen berhasil disimpan",
    "data": {
        "predicted_days_to_harvest": "111 hari",
        "recommended_feed": "2.92 kg"
    }
}

```
  - Error Respone:

  Code: 500
```http
 {
    "message": 'Gagal menyimpan data prediksi panen',
    "error": error.message
}

```

## Delete prediksi panen ikan

- URL
```http
  DELETE /prediksi/{id}
```

- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Respone:
  
  Code: 200
```http
{
    "message": "Prediksi panen berhasil dihapus"
}

```
  
- Error Respone:

  Code: 404
```http
 {
    "message": "Data prediksi panen tidak ditemukan atau tidak milik user ini",
}
```

Code: 500
```http
 {
    "message": "Gagal menghapus prediksi panen",
    "error": error.message
}
```

## Dropdown jenis ikan

- URL
```http
  GET /api/fish-types
```

- Headers:
    
    Content-Type: application/json

- Succes Respone:
  
  Code: 200
```http
{
    "message": "Daftar jenis ikan berhasil diambil"
}

```
  
- Error Respone:

  Code: 500
```http
 {
    "message": "Gagal mengambil daftar jenis ikan",
}
```
