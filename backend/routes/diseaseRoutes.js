const express =require('express')



const {addDisease,getDiseaseByName,updateDisease,deleteDisease}=require('../controllers/diseaseController')

const router = express.Router();

router.post('/', addDisease);
router.put('/name/:name', updateDisease);
router.get('/name/:name', getDiseaseByName)
router.delete('/name/:name', deleteDisease);

module.exports = router;
