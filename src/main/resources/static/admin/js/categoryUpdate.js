let imageUploadedCatUp = false;

// Şəkil yükləmə funksiyası
function handleImageUploadCatUp(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('imagePreviewCatUp');
            const removeButton = document.getElementById('removeImageButtonCatUp');
            const uploadButton = document.getElementById('uploadImageButtonCatUp');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';

            imageUploadedCatUp = true;
            document.getElementById('imageErrorCatUp').style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function removeImageCatUp() {
    const preview = document.getElementById('imagePreviewCatUp');
    const removeButton = document.getElementById('removeImageButtonCatUp');
    const uploadButton = document.getElementById('uploadImageButtonCatUp');
    const hiddenInput = document.getElementById('imageUrlHiddenCatUp'); // imageUrl hidden inputu

    // Şəkil URL-i boşaldılır
    hiddenInput.value = ''; // Serverdən gələn şəkilin URL-i silinir

    document.getElementById('imageInputCatUp').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';

    imageUploadedCatUp = false;
}

// Alt kateqoriya əlavə funksiyası
function addSubcategoryUpdate() {
    const container = document.getElementById('SubcategoriesContainer');

    const inputWrapper = document.createElement('div');
    inputWrapper.style.display = 'flex';
    inputWrapper.style.alignItems = 'center';
    inputWrapper.style.marginBottom = '10px';

    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.name = 'subcategoryNames';
    newInput.placeholder = 'Daha bir alt kateqoriya';
    newInput.style.cssText = 'margin-right: 5px; width: 100%; height: 40px; padding: 10px; outline: none; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); border: 1px solid #ccc; border-radius: 5px;';

    const removeButton = document.createElement('button');
    removeButton.innerText = 'Sil';
    removeButton.type = 'button';
    removeButton.className = 'btn btn-primary py-1 px-2';
    removeButton.style.backgroundColor = '#FF4D4D';
    removeButton.style.borderColor = '#FF4D4D';
    removeButton.onmouseover = function () {
        this.style.backgroundColor = '#FF2929';
    };
    removeButton.onmouseout = function () {
        this.style.backgroundColor = '#FF4D4D';
    };
    removeButton.onclick = function () {
        removeSubcategoryUpdate(this);
    };

    inputWrapper.appendChild(newInput);
    inputWrapper.appendChild(removeButton);
    container.appendChild(inputWrapper);
}

// Alt kateqoriya silmə funksiyası
function removeSubcategoryUpdate(button) {
    const container = document.getElementById('SubcategoriesContainer');
    if (container.children.length > 1) {
        button.parentElement.remove();
    } else {
        alert("Ən azı bir alt kateqoriya olmalıdır!");
    }
}

// Form göndərməzdən əvvəl yoxlama funksiyası
function validateAndSubmitFormUpdate() {
    const container = document.getElementById('SubcategoriesContainer');
    const inputs = container.querySelectorAll('input[name="subcategoryNames"]');
    const hiddenInput = document.getElementById('imageUrlHiddenCatUp'); // Serverdən gələn şəkilin URL-i
    const imageInput = document.getElementById('imageInputCatUp'); // Müştərinin yüklədiyi şəkil


    let isValid = true;

    // Alt kateqoriyalar yoxlanır
    inputs.forEach(input => {
        if (input.value.trim() === '') {
            if (container.children.length > 1) {
                input.parentElement.remove();
            } else {
                alert("Birinci alt kateqoriya boş ola bilməz!");
                isValid = false;
            }
        }
    });

    // Şəkil yoxlanır (hər iki input boşdursa, xəbərdarlıq göstərilir)
        const imageUploaded = hiddenInput.value.trim() !== '' || imageInput.files.length > 0;
        if (!imageUploaded) {
            document.getElementById('imageErrorCatUp').style.display = 'block'; // Xəbərdarlıq göstərilir
            isValid = false;
        } else {
            document.getElementById('imageErrorCatUp').style.display = 'none'; // Xəbərdarlıq gizlədilir
        }

    return isValid; // Əgər hər şey düzgündürsə, form göndəriləcək
}

// Serverdən gələn şəkil varsa, "Şəkil Yüklə" düyməsini gizlətmək və "Şəkil Sil" düyməsini göstərmək
document.addEventListener('DOMContentLoaded', function () {
    const imageUrl = document.getElementById('imageUrlHiddenCatUp').value;
    const preview = document.getElementById('imagePreviewCatUp');
    const uploadButton = document.getElementById('uploadImageButtonCatUp');
    const removeButton = document.getElementById('removeImageButtonCatUp');
    const hiddenInput = document.getElementById('imageUrlHiddenCatUp');

    if (imageUrl) {
        preview.src = imageUrl;
        preview.style.display = 'block';
        uploadButton.style.display = 'none';
        removeButton.style.display = 'block';
        imageUploadedCatUp = true;
        document.getElementById('imageErrorCatUp').style.display = 'none';  // Serverdən gələn şəkil varsa, xəbərdarlığı gizlət
    }
});
