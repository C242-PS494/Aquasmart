# Aquasmart Introduction

Aquasmart is a solution designed for real-time monitoring of water quality and fish farming predictions. By analyzing water parameters like pH, turbidity, and temperature, along with environmental data, Aquasmart provides insights to optimize fish farming practices and predict harvest times. Using machine learning and cloud technologies, it empowers farmers, communities, and organizations to manage water resources efficiently and make data-driven decisions for better productivity and sustainability


# How To Deploy To Google Compute Engine
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
  git clone -b main https://github.com/C242-PS494/Aquasmart.git
```

Go to the project directory

```bash
  cd vm-backend
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

## Register

- URL
```http
  POST /auth/register
```
- URL Params

  Required: language = `[string]` 'indonesia'


- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Body Request:
```http
 {
  "name": "cahya",
  "email": "cahya@gmail.com",
  "dateBirth": "2003-01-01",
  "phoneNumber": "081234567899",
  "password": "cahya123"
}
```

- Success Respone:
  
  Code: 200
```http
  {
    "status": "success",
    "message": "User berhasil didaftarkan.",
    "data": {
        "userId": ""
    }
}
```

- Error Respone:

  Code: 500
```http
 {
    "status": "error",
    "message": "Terjadi kesalahan pada server."
}

```

## Login

- URL
```http
  POST /auth/login
```
- URL Params

  Required: language = `[string]` 'indonesia'

- Body Request:
```http
{
  "email": "cahya@gmail.com",
  "password": "cahya123"
}
```

- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Respone:
  
  Code: 200
```http
{
    "status": "success",
    "message": "Login berhasil",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZENLNHRqMkk3anVKUDkiLCJlbWFpbCI6ImNhaHlhQGdtYWlsLmNvbSIsImlhdCI6MTczMzEyOTg2MSwiZXhwIjoxNzMzMTMzNDYxfQ.YfrjLktKKw6-xTSs3tUricP2lnCgMWaln27ddhISrFc"
}

```
  - Error Respone:

    Code: 500
```http
    "status": "error",
    "message": "Terjadi kesalahan pada server."

```
    

Code: 400
```http
    "status": "fail",
    "message": "Email atau password salah."

```
## Profile

- URL
```http
  GET /profile
```

- Headers:
    
    Content-Type: application/json
    
    Authorization: Bearer `JWT Token`

- Succes Respone:
  
  Code: 200
```http
{
    "status": "success",
    "data": {
        "id": "",
        "name": "",
        "email": "",
        "phoneNumber": "",
        "dateBirth": "",
        "profilePicture": "https://example.com/default-profile-picture.jpg"
    }
}

```
  
- Error Respone:

  Code: 404
```http
 {
    "status": "success",
    "message": "Pengguna tidak ditemukan.",
}
```

Code: 500
```http
 {
    "status": "failed",
    "message": "Terjadi kesalahan pada server.",
}
```

## Update Photo Profile

- URL
```http
  PUT /profile/picture
```

- Headers:
    
    Content-Type: multipart/form-data

- Body Request:

    Key: profilePicture
    
    Value: File



- Succes Respone:
  
  Code: 200
```http
{
    "status": "success",
    "message": "Foto profil berhasil diupdate."
}

```
  
- Error Respone:

  Code: 500
```http
 {
    "status": "error",
    "message": "agal mengupdate foto profil. Terjadi kesalahan server.",
}
```
