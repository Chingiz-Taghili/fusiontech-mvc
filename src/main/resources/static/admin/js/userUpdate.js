let imageAddedUsUp = false;

// Şəkil yükləmə funksiyası
function imageAddUsUp(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('imageUsUp');
            const removeButton = document.getElementById('delButtonUsUp');
            const uploadButton = document.getElementById('addButtonUsUp');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';

            imageAddedUsUp = true;
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function delImageUsUp() {
    const preview = document.getElementById('imageUsUp');
    const removeButton = document.getElementById('delButtonUsUp');
    const uploadButton = document.getElementById('addButtonUsUp');
    const hiddenInput = document.getElementById('oldImageUsUp'); // image hidden inputu

    // Şəkil URL-i boşaldılır
    hiddenInput.value = ''; // Serverdən gələn şəkilin URL-i silinir

    document.getElementById('imageInputUsUp').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';

    imageAddedUsUp = false;
}

// Serverdən gələn şəkil varsa, "Şəkil Yüklə" düyməsini gizlətmək və "Şəkil Sil" düyməsini göstərmək
document.addEventListener('DOMContentLoaded', function () {
    const image = document.getElementById('oldImageUsUp').value;
    const preview = document.getElementById('imageUsUp');
    const uploadButton = document.getElementById('addButtonUsUp');
    const removeButton = document.getElementById('delButtonUsUp');
    const hiddenInput = document.getElementById('oldImageUsUp');

    if (image) {
        preview.src = image;
        preview.style.display = 'block';
        uploadButton.style.display = 'none';
        removeButton.style.display = 'block';
        imageAddedUsUp = true;
    }
});
