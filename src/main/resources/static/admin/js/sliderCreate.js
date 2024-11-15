let sliderImageAddedCr = false;

// Şəkil yükləmə funksiyası
function handleSliderImageAddCr(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('sliderImagePreviewCr');
            const removeButton = document.getElementById('removeSliderImageButtonCr');
            const uploadButton = document.getElementById('addSliderImageButtonCr');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';

            sliderImageAddedCr = true;
            document.getElementById('sliderImageErrorCr').style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function removeSliderImageCr() {
    const preview = document.getElementById('sliderImagePreviewCr');
    const removeButton = document.getElementById('removeSliderImageButtonCr');
    const uploadButton = document.getElementById('addSliderImageButtonCr');

    document.getElementById('sliderImageInputCr').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';

    sliderImageAddedCr = false;
}

// Form göndərməzdən əvvəl yoxlama funksiyası
function validateFormSliderCreate() {

    let isValid = true;

    // Şəkil yoxlanır
    if (!sliderImageAddedCr) {
        document.getElementById('sliderImageErrorCr').style.display = 'block';
        isValid = false;
    }

    return isValid; // Əgər hər şey düzgündürsə, form göndəriləcək
}
