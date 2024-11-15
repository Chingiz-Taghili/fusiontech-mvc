let imageAddedSliderUp = false;

// Şəkil yükləmə funksiyası
function imageUploadSliderUp(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('imageSliderUp');
            const removeButton = document.getElementById('removeButtonSliderUp');
            const uploadButton = document.getElementById('addImageButtonSliderUp');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';

            imageAddedSliderUp = true;
            document.getElementById('errorImageSliderUp').style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function removeImageSliderUp() {
    const preview = document.getElementById('imageSliderUp');
    const removeButton = document.getElementById('removeButtonSliderUp');
    const uploadButton = document.getElementById('addImageButtonSliderUp');
    const hiddenInput = document.getElementById('oldImageUrlSliderUp'); // imageUrl hidden inputu

    // Şəkil URL-i boşaldılır
    hiddenInput.value = ''; // Serverdən gələn şəkilin URL-i silinir

    document.getElementById('imageInputSliderUp').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';

    imageAddedSliderUp = false;
}

// Form göndərməzdən əvvəl yoxlama funksiyası
function validateFormSliderUpdate() {
    const hiddenInput = document.getElementById('oldImageUrlSliderUp'); // Serverdən gələn şəkilin URL-i
    const imageInput = document.getElementById('imageInputSliderUp'); // Müştərinin yüklədiyi şəkil

    let isValid = true;

    // Şəkil yoxlanır (hər iki input boşdursa, xəbərdarlıq göstərilir)
        const imageUploaded = hiddenInput.value.trim() !== '' || imageInput.files.length > 0;
        if (!imageUploaded) {
            document.getElementById('errorImageSliderUp').style.display = 'block'; // Xəbərdarlıq göstərilir
            isValid = false;
        } else {
            document.getElementById('errorImageSliderUp').style.display = 'none'; // Xəbərdarlıq gizlədilir
        }

    return isValid; // Əgər hər şey düzgündürsə, form göndəriləcək
}

// Serverdən gələn şəkil varsa, "Şəkil Yüklə" düyməsini gizlətmək və "Şəkil Sil" düyməsini göstərmək
document.addEventListener('DOMContentLoaded', function () {
    const imageUrl = document.getElementById('oldImageUrlSliderUp').value;
    const preview = document.getElementById('imageSliderUp');
    const uploadButton = document.getElementById('addImageButtonSliderUp');
    const removeButton = document.getElementById('removeButtonSliderUp');
    const hiddenInput = document.getElementById('oldImageUrlSliderUp');

    if (imageUrl) {
        preview.src = imageUrl;
        preview.style.display = 'block';
        uploadButton.style.display = 'none';
        removeButton.style.display = 'block';
        imageAddedSliderUp = true;
        document.getElementById('errorImageSliderUp').style.display = 'none';  // Serverdən gələn şəkil varsa, xəbərdarlığı gizlət
    }
});
