const express = require('express')



const {
    addDisease,
    getDiseasesByUserId,
    getDiseaseByNameAndUserId,
    updateDiseaseByUserId,
    deleteDiseaseByUserId
} = require('../controllers/diseaseController')

const router = express.Router();

router.post('/', addDisease);
router.put('/user/:userId/name/:name', updateDiseaseByUserId);
router.get('/user/:userId', getDiseasesByUserId);
router.get('/user/:userId/name/:name', getDiseaseByNameAndUserId);
router.delete('/user/:userId/name/:name', deleteDiseaseByUserId);

module.exports = router;
