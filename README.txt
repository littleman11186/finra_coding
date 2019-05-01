File upload and download coding exercise

	Goal: to create a RESTful Api for uploading and downloading files to the local file storage and attaching metadata in an internal H2 memory database.

	API

	/upload
		Accepts: MultipartForm
		Fields:
			- file - multipart file field
			- owner - string name of the owner of the file
			- description - string description of the file itself
		Returns: Json metadata of the file if successful including the "id" field

	
	/fileMeta/{fileId}
		Accepts: Path driven file id
		Fields:
			- fileId - path input with the number associated with the file upload
		Returns: Json metadata of the file 

	/fileContent/{fileId}
		Accepts: Path driven file id
		Fields:
			- fileId - path input with the number associated with the file upload
		Returns: Byte array with the file contents

	/owners/{owner_name}
		Accepts: Path driven file id
		Fields:
			- owner_name - Owner name to return associate file ID's for
		Returns: Array containing comma sepparated numbers wrapped with []