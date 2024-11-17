// Şəkil yükləmə funksiyası
function imageUploadRegister(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('imagePreviewRegister');
            const removeButton = document.getElementById('delImageButtonRegister');
            const uploadButton = document.getElementById('uploadImageButtonRegister');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function removeImageRegister() {
    const preview = document.getElementById('imagePreviewRegister');
    const removeButton = document.getElementById('delImageButtonRegister');
    const uploadButton = document.getElementById('uploadImageButtonRegister');

    document.getElementById('imageInputRegister').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';
}
