document.addEventListener("DOMContentLoaded", function () {
    // Hal-hazırda aktiv URL-i al
    const currentUrl = window.location.pathname;

    // Bütün .nav-link-page olan a tag-larını seç
    document.querySelectorAll(".nav-link-page").forEach(link => {
        const href = link.getAttribute("href");

        // Mağaza səhifəsi, axtarış səhifəsi və ona aid URL-lər üçün uyğunluğu yoxlayırıq
        if ((currentUrl === "/shop" || currentUrl.startsWith("/shop/") || currentUrl.startsWith("/search")) &&
            (href === "/shop" || href === "/shop/filter" || href.startsWith("/shop/") || href.startsWith("/search"))) {
            link.classList.add("active");
        }
        // Digər səhifələr üçün uyğunluğu yoxlayırıq
        else if (currentUrl === href) {
            link.classList.add("active");
        }
    });
});
