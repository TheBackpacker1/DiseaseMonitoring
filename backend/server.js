const express = require('express');
const connectDB = require('./config/db');
const diseaseRoutes = require('./routes/diseaseRoutes');

const app = express();

connectDB();


// Middleware to parse JSON
app.use(express.json());


// Use the routes
app.use('/api/diseases', diseaseRoutes);

app.get('/', (req, res) => {
    res.send('API is running');
  });

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));