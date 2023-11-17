function showProducts() {
    var content = document.getElementById('content');
    
    fetch('http://localhost:8080/products')
        .then(response => response.json()) // Parse the response as JSON
        .then(data => {
            console.log("test");
            let productListHTML = `<h1>List of Products</h1>`;
            content.innerHTML = '<p>ok.</p>';
            productListHTML += '<ul>';

            data.forEach(product => {
                productListHTML += '<li>';
                productListHTML += `<strong>Product ID:</strong> ${product.productId}<br>`;
                productListHTML += `<strong>Name:</strong> ${product.name}<br>`;
                productListHTML += `<strong>Description:</strong> ${product.description}<br>`;
                productListHTML += `<strong>Price:</strong> ${product.price}<br>`;
                productListHTML += `<strong>Category:</strong> ${product.category}<br>`;
                productListHTML += `<strong>Stock Quantity:</strong> ${product.stockQuantity}<br>`;
                //productListHTML += `<strong>Images:</strong> ${product.images.join(', ')}<br>`;
                //productListHTML += `<strong>Created At:</strong> ${product.createdAt}<br>`;
                //productListHTML += `<strong>Updated At:</strong> ${product.updatedIt}<br>`;
                productListHTML += '</li>';
            });

            productListHTML += '</ul>';
            content.innerHTML = productListHTML;
        })
        .catch(error => {
            console.error('Error fetching products:', error);
            content.innerHTML = '<p>Error fetching products. Please try again later.</p>';
        });
}
        function showAddProductForm() {
            var content = document.getElementById('content');
            content.innerHTML = '<h1>Add a New Product</h1>';
            var formContainer = document.createElement('div');
            var form = document.createElement('form');
            formContainer.classList.add('form-container');
            content.innerHTML = 
                `<label for="name" class="form-label">Name: </label>
                <input type="text" name="name" class="form-input"><br>

                <label for="description" class="form-label">Description: </label>
                <input type="text" name="description" class="form-input"><br>

                <label for="price" class="form-label">Price: </label>
                <input type="number" name="price" step="0.01" class="form-input"><br>

                <label for="category" class="form-label">Category: </label>
                <input type="text" name="category" class="form-input"><br>

                <label for="quantity" class="form-label">Quantity: </label>
                <input type="number" name="quantity" class="form-input"><br>`;
                formContainer.appendChild(form);
                content.appendChild(formContainer);
        }

