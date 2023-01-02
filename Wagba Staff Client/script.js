 // Import the functions you need from the SDKs you need
 import { initializeApp } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-app.js";
 import { getAnalytics } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-analytics.js";
 import { getDatabase, ref, child, get, onValue, set, update} from "https://www.gstatic.com/firebasejs/9.15.0/firebase-database.js";
 import { getAuth, signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-auth.js";

 // For more SDKs for Firebase products that you want to use
 // https://firebase.google.com/docs/web/setup#available-libraries

 // Your web app's Firebase configuration
 // For Firebase JS SDK v7.20.0 and later, measurementId is optional
 const firebaseConfig = {
   apiKey: "AIzaSyBpunG30Y3g-pumFkYPFRfpvk3utMVVy0Y",
   authDomain: "wagba-22208.firebaseapp.com",
   databaseURL: "https://wagba-22208-default-rtdb.europe-west1.firebasedatabase.app",
   projectId: "wagba-22208",
   storageBucket: "wagba-22208.appspot.com",
   messagingSenderId: "598622247479",
   appId: "1:598622247479:web:ed6a4aebe563dbe27f7db2",
   measurementId: "G-05346WR3SC"
 };

 // Initialize Firebase
 const app = initializeApp(firebaseConfig);
 const auth = getAuth();
 const database = getDatabase(app);
 const analytics = getAnalytics(app);
 
 const UnconfirmedOrdersContainer = document.querySelector('#UnconfirmedOrders');
 const confirmedOrdersContainer = document.querySelector('#confirmedOrders');

 signInWithEmailAndPassword(auth, "staff@wagba.com", "Mazen12")
 .then((userCredential) => {
   console.log("Signed in");
 })
 .catch((error) => {
   const errorCode = error.code;
   const errorMessage = error.message;
   console.log(error.message);
 });

 onValue(ref(database, 'Orders'), (orders) => {
       UnconfirmedOrdersContainer.innerHTML = '';
       confirmedOrdersContainer.innerHTML = ''; 
       orders.forEach((order) => {
       if (order.val().orderStatus == "Verifying"){
           const orderCard = generateOrderCard(order.val(), true);
           UnconfirmedOrdersContainer.appendChild(orderCard);
       }
       else {
           const orderCard = generateOrderCard(order.val(), false);
           confirmedOrdersContainer.appendChild(orderCard);
       }
       });
   }, {
     onlyOnce: false
   });
   
   function generateOrderCard(order, createButtons) {
     const orderCard = document.createElement('div');
     orderCard.classList.add('order-card');

     const orderId = document.createElement('div');
     orderId.classList.add('order-id');
     orderId.textContent = `Order ID: ${order.orderId}`;

     const orderTime = document.createElement('div');
     orderTime.classList.add('order-time');
     orderTime.textContent = `Order Time: ${order.orderTime}`;

     const orderRestaurant = document.createElement('div');
     orderRestaurant.classList.add('order-restaurant');
     orderRestaurant.textContent = `Restaurant: ${order.restaurant}`;
     
     const orderStatus = document.createElement('div');
     orderStatus.classList.add('order-status');
     orderStatus.textContent = `Order Status: ${order.orderStatus}`;

     const orderPrice = document.createElement('div');
     orderPrice.classList.add('order-price');
     orderPrice.textContent = `Order Price: ${order.totalPrice} EGP`;

     orderCard.appendChild(orderId);
     orderCard.appendChild(orderRestaurant);
     orderCard.appendChild(orderStatus);
     orderCard.appendChild(orderTime);
     orderCard.appendChild(orderPrice);
     
       if (createButtons){
     const buttons = document.createElement('div');
     buttons.classList.add('buttons');

     const confirmButton = document.createElement('button');
     confirmButton.classList.add('confirm-button');
     confirmButton.textContent = 'Confirm';
     confirmButton.addEventListener('click', () => {
       console.log(`Order ${order.orderId} confirmed`);
       orderCard.parentElement.removeChild(orderCard);
       update(ref(database, 'Users/'+order.userId +'/Orders/'+order.orderId),{
           "orderStatus": "Preparing" 
       });
       update(ref(database, 'Orders/'+order.orderId),{
           "orderStatus": "Preparing" 
       });
     });

     const declineButton = document.createElement('button');
     declineButton.classList.add('decline-button');
     declineButton.textContent = 'Decline';
     declineButton.addEventListener('click', () => {
       console.log(`Order ${order.orderId} declined`);
       orderCard.parentElement.removeChild(orderCard);
       update(ref(database, 'Users/'+order.userId +'/Orders/'+order.orderId),{
           "orderStatus": "Denied" 
       });
       update(ref(database, 'Orders/'+order.orderId),{
           "orderStatus": "Denied" 
       });
     });

     buttons.appendChild(confirmButton);
     buttons.appendChild(declineButton);
     
     orderCard.appendChild(buttons);

     }

     return orderCard;
   }