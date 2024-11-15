let imageUploadedCatCr = false;

// Şəkil yükləmə funksiyası
function handleImageUploadCatCr(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('imagePreviewCatCr');
            const removeButton = document.getElementById('removeImageButtonCatCr');
            const uploadButton = document.getElementById('uploadImageButtonCatCr');

            preview.src = e.target.result;
            preview.style.display = 'block';
            removeButton.style.display = 'block';
            uploadButton.style.display = 'none';

            imageUploadedCatCr = true;
            document.getElementById('imageErrorCatCr').style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
}

// Şəkil silmə funksiyası
function removeImageCatCr() {
    const preview = document.getElementById('imagePreviewCatCr');
    const removeButton = document.getElementById('removeImageButtonCatCr');
    const uploadButton = document.getElementById('uploadImageButtonCatCr');

    document.getElementById('imageInputCatCr').value = '';
    preview.src = '';
    preview.style.display = 'none';
    removeButton.style.display = 'none';
    uploadButton.style.display = 'flex';

    imageUploadedCatCr = false;
}

// Alt kateqoriya əlavə funksiyası
function addSubcategoryCreate() {
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
        removeSubcategoryCreate(this);
    };

    inputWrapper.appendChild(newInput);
    inputWrapper.appendChild(removeButton);
    container.appendChild(inputWrapper);
}

// Alt kateqoriya silmə funksiyası
function removeSubcategoryCreate(button) {
    const container = document.getElementById('SubcategoriesContainer');
    if (container.children.length > 1) {
        button.parentElement.remove();
    } else {
        alert("Ən azı bir alt kateqoriya olmalıdır!");
    }
}

// Form göndərməzdən əvvəl yoxlama funksiyası
function validateAndSubmitFormCreate() {
    const container = document.getElementById('SubcategoriesContainer');
    const inputs = container.querySelectorAll('input[name="subcategoryNames"]');

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

    // Şəkil yoxlanır
    if (!imageUploadedCatCr) {
        document.getElementById('imageErrorCatCr').style.display = 'block';
        isValid = false;
    }

    return isValid; // Əgər hər şey düzgündürsə, form göndəriləcək
}
