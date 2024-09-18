document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('.text-primary label');
    let selectedIndex = -1; // Seçilmiş ulduzun indexi (-1 = heç nə seçilməyib)

    // Xəbərdarlıq mesajları üçün HTML elementlərini yarat
    const ratingWarning = document.getElementById('ratingWarning');
    const commentWarning = document.getElementById('commentWarning');

    stars.forEach((star, index) => {
        star.addEventListener('mouseover', function() {
            resetStars(); // Bütün ulduzları ilkin vəziyyətə qaytar
            for (let i = index; i < stars.length; i++) {
                stars[i].querySelector('i').classList.remove('far');
                stars[i].querySelector('i').classList.add('fas');
            }
        });

        star.addEventListener('mouseout', function() {
            resetStars(); // Kursor çıxan zaman ulduzları ilkin vəziyyətə qaytar
            if (selectedIndex !== -1) { // Seçilmiş bir ulduz varsa, onu göstər
                for (let i = 0; i <= selectedIndex; i++) {
                    stars[i].querySelector('i').classList.remove('far');
                    stars[i].querySelector('i').classList.add('fas');
                }
            }
        });

        star.addEventListener('click', function() {
            if (selectedIndex === index) {
                // Əgər eyni ulduza kliklənib, seçimi ləğv edir
                selectedIndex = -1;
                resetStars();
            } else {
                // Yeni ulduza kliklənib, seçimi yeniləyir
                selectedIndex = index;
                resetStars();
                for (let i = 0; i <= selectedIndex; i++) {
                    stars[i].querySelector('i').classList.remove('far');
                    stars[i].querySelector('i').classList.add('fas');
                }
            }
        });
    });

    function resetStars() {
        stars.forEach(star => {
            star.querySelector('i').classList.remove('fas');
            star.querySelector('i').classList.add('far');
        });
    }

    // Form Yoxlanması və Xəbərdarlıq Mesajları
    document.getElementById('reviewForm').addEventListener('submit', function(event) {
        var ratingSelected = document.querySelector('input[name="rating"]:checked');
        var commentText = document.getElementById('message').value.trim();
        let valid = true; // Form yoxlaması üçün flag

        // Ulduz seçilməyibsə, xəbərdarlıq göstər
        if (!ratingSelected) {
            ratingWarning.style.display = 'block';
            valid = false; // Formu göndərməyə imkan vermə
        } else {
            ratingWarning.style.display = 'none'; // Xəbərdarlığı gizlə
        }

        // Rəy yazılmayıbsa, xəbərdarlıq göstər
        if (commentText === "") {
            commentWarning.style.display = 'block';
            valid = false; // Formu göndərməyə imkan vermə
        } else {
            commentWarning.style.display = 'none'; // Xəbərdarlığı gizlə
        }

        if (!valid) {
            event.preventDefault(); // Əgər hansısa problem varsa formu göndərməyə imkan vermə
        }
    });



});
