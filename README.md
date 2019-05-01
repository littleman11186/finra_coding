# File upload and download coding exercise

## Goal:
to create a RESTful Api for uploading and downloading files to the local file storage and attaching metadata in an internal H2 memory database.

## API:

```
POST	/upload
		Accepts: MultipartForm
		Fields:
			- file - multipart file field
			- owner - string name of the owner of the file
			- description - string description of the file itself
		Returns: Json metadata of the file if successful including the "id" field
```

```
	
GET	/fileMeta/{fileId}
		Accepts: Path driven file id
		Fields:
			- fileId - path input with the number associated with the file upload
		Returns: Json metadata of the file 
```
```
GET	/fileContent/{fileId}
		Accepts: Path driven file id
		Fields:
			- fileId - path input with the number associated with the file upload
		Returns: Byte array with the file contents
```
```
GET	/owners/{owner_name}
		Accepts: Path driven file id
		Fields:
			- owner_name - Owner name to return associate file ID's for
		Returns: Array containing comma sepparated numbers wrapped with []
```


## Configuration:

application.properties

	fileStoragePath={drive_path} - desired path for the file uploads to be managed inside of. Will create directory if it does not exist
	
	overwriteExisting={true/false} - Optional, default false - desired functionality when finding duplicate files to throw an error or simply overwrite with new values and return success