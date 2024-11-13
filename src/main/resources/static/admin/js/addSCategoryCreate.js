// Dinamik şəkildə alt kateqoriya inputu əlavə edən funksiya (CREATE)
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
    newInput.style.cssText = 'margin-right: 5px; width: 400px; height: 40px; padding: 10px; outline: none; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); border: 1px solid #ccc; border-radius: 5px;';

    const removeButton = document.createElement('button');
    removeButton.innerText = 'Ləğv et';
    removeButton.type = 'button';
    removeButton.className = 'btn btn-primary py-1 px-2';
    removeButton.onclick = function () {
        removeSubcategoryCreate(this);
    };

    inputWrapper.appendChild(newInput);
    inputWrapper.appendChild(removeButton);
    container.appendChild(inputWrapper);
}

// Inputu silən funksiya (CREATE)
function removeSubcategoryCreate(button) {
    const container = document.getElementById('SubcategoriesContainer');
    if (container.children.length > 1) {
        button.parentElement.remove();
    } else {
        alert("Ən azı bir alt kateqoriya olmalıdır!");
    }
}

// Form göndərilməzdən əvvəl yoxlama funksiyası (CREATE)
function validateAndSubmitFormCreate() {
    const container = document.getElementById('SubcategoriesContainer');
    const inputs = container.querySelectorAll('input[name="subcategoryNames"]');

    let isValid = true;

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

    return isValid;
}
