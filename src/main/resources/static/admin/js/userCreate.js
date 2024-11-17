// Şəkil yükləmə funksiyası
function imageUploadUsCr(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('imagePreviewUsCr');
            const removeButton = document.getElementById('delImageButtonUsCr');
            const uploadButton = document.getElementById('uploadImageButtonUsCr');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function removeImageUsCr() {
    const preview = document.getElementById('imagePreviewUsCr');
    const removeButton = document.getElementById('delImageButtonUsCr');
    const uploadButton = document.getElementById('uploadImageButtonUsCr');

    document.getElementById('imageInputUsCr').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';
}
