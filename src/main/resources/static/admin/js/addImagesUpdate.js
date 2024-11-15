// Yeni şəkilləri saxlayan array
let newImages = [];

// Şəkil əlavə edildikdə `newImages` array-ə şəkil fayllarını əlavə edirik
document.getElementById("imageUploadUpdate").onchange = function() {
    const imageContainer = document.getElementById("imagePreviewContainerUpdate");

    Array.from(this.files).forEach(file => {
        // Şəkil faylını `newImages` array-ə əlavə edirik
        newImages.push(file);

        const reader = new FileReader();
        reader.onload = function(e) {
            const imgContainer = document.createElement("div");
            imgContainer.style.position = "relative";
            imgContainer.style.display = "inline-block";
            imgContainer.style.flex = "0 0 19%";


            const img = document.createElement("img");
            img.src = e.target.result;
            img.style.width = "100%";
            img.style.height = "100%";
            img.style.borderRadius = "5px";
            img.style.objectFit = "cover";
            img.style.aspectRatio = "1";

            // Modal açma funksiyası
            img.onclick = function() { openModal(e.target.result); };

            const deleteBtn = document.createElement("button");
            deleteBtn.innerHTML = "X";
            deleteBtn.style.position = "absolute";
            deleteBtn.style.top = "5px";
            deleteBtn.style.right = "5px";
            deleteBtn.style.backgroundColor = "red";
            deleteBtn.style.color = "white";
            deleteBtn.style.border = "none";
            deleteBtn.style.borderRadius = "50%";
            deleteBtn.style.width = "20px";
            deleteBtn.style.height = "20px";
            deleteBtn.style.fontSize = "12px";
            deleteBtn.style.cursor = "pointer";
            deleteBtn.style.display = "none";  // Hover zamanı "X" düyməsi görünməlidir

            deleteBtn.onclick = function() {
                imgContainer.remove();
                const index = newImages.indexOf(file);
                if (index > -1) {
                    newImages.splice(index, 1); // Array-dən silirik
                }
                updateNewImagesContainer(); // Yenilənmiş faylları input sahəsinə göndəririk
            };

            imgContainer.appendChild(img);
            imgContainer.appendChild(deleteBtn);
            imageContainer.appendChild(imgContainer);

            // Dinamik inputları yeniləyirik
            updateNewImagesContainer();

            // Hover effekti əlavə edirik
            imgContainer.onmouseover = function() { deleteBtn.style.display = "block"; };
            imgContainer.onmouseout = function() { deleteBtn.style.display = "none"; };
        };
        reader.readAsDataURL(file);
    });
};

// Dinamik olaraq input əlavə etmə funksiyası
function updateNewImagesContainer() {
    const newImagesContainer = document.getElementById("newImagesContainer");
    newImagesContainer.innerHTML = ""; // Mövcud faylları təmizləyirik

    newImages.forEach((file, index) => {
        const input = document.createElement("input");
        input.type = "file";
        input.name = "newImages";
        input.class = "hidden-input";
        input.files = createFileList([file]);
        input.style.display = "none"; // Gizli edirik
        newImagesContainer.appendChild(input);
    });
}

// Helper funksiyası: Faylları input-a əlavə etmək üçün
function createFileList(files) {
    const dataTransfer = new DataTransfer();
    files.forEach(file => dataTransfer.items.add(file));
    return dataTransfer.files;
}

// Modal açma funksiyası
function openModal(imageSrc) {
    const modal = document.getElementById("imageModal");
    const modalImage = document.getElementById("modalImage");
    modalImage.src = imageSrc;
    modal.style.display = "flex";
}

// Modalı bağlama funksiyası
function closeModal() {
    const modal = document.getElementById("imageModal");
    modal.style.display = "none";
}

// Şəkil əlavə edilmədiyi halda formu təqdim etməyə mane olur
function validateAndSubmitForm() {
    const imageInput = document.getElementById('imageUploadUpdate');
    const imageWarningUpdate = document.getElementById('imageWarningUpdate');
    const imageCount = imageInput.files.length;

    if (imageCount === 0 && newImages.length === 0) { // `newImages` də boşdursa xəbərdarlıq göstərir
        imageWarningUpdate.style.display = 'block';
        return false; // Formun təqdim edilməsini dayandırır
    }

    imageWarningUpdate.style.display = 'none'; // Əgər şəkil əlavə edilibsə, xəbərdarlığı gizlədir
    return true;
}

// Serverdən gələn şəkilləri silmək üçün funksiya
function removeImage(button, imageUrl) {
    const imgContainer = button.parentElement;
    imgContainer.remove();

    const hiddenInputs = document.querySelectorAll('input.hidden-image');
    hiddenInputs.forEach(input => {
        if (input.value === imageUrl) {
            input.remove();
        }
    });
}

// Formun təqdim edilmə funksiyası
document.getElementById("formSubmitButton").addEventListener("click", function(event) {
    event.preventDefault(); // Formun təqdim edilməsini dayandırırıq

    const form = document.getElementById("formId"); // Formun ID-sini düzgün qoyun

    if (validateAndSubmitForm()) { // Yalnız şəkillər varsa formu göndər
        form.submit(); // Formu təqdim edir
    }
});
