const Disease = require('../models/Disease')


const staticUserId ="64fef678e1b2ec4f112d4f89"


const addDisease = async (req, res) => {

  const { userId, name, medicine, prescriptionTimes, prescription, notificationTime, diagnosedAt } = req.body

  try {
    const newDisease = new Disease({
      userId :staticUserId,
      name,
      medicine,
      prescriptionTimes,
      prescription,
      notificationTime,
      diagnosedAt,
    });
    await newDisease.save();
    res.status(201).json(newDisease);
  } catch (error) {
    res.status(500).json({ message: 'Failed to add disease', error: error.message });
  }




}

const getDiseasesByUserId = async (req, res) => {
  const { userId } = req.params; 

  try {
    const disease = await Disease.find({ userId }); 

    if (!disease.length) {
      return res.status(404).json({ message: 'No diseases found for this user' });
    }

    res.json(disease);
  } catch (error) {
    res.status(500).json({ message: 'Failed to retrieve disease', error: error.message });
  }
};

const getDiseaseByNameAndUserId = async (req, res) => {
  const { userId, name } = req.params;

  try {
    const disease = await Disease.findOne({ userId, name });
    if (!disease) {
      return res.status(404).json({ message: 'Disease not found' });
    }
    res.json(disease);
  } catch (error) {
    res.status(500).json({ message: 'Failed to retrieve disease', error: error.message });
  }
};

const updateDiseaseByUserId  = async (req, res) => {
  const {userId,name}=req.params 
  const { medicine, prescriptionTimes, prescription, notificationTime, lastUpdated } = req.body;

  try {
    const updatedDisease = await Disease.findOneAndUpdate(
      { userId,name },
      { medicine, prescriptionTimes, prescription, notificationTime, lastUpdated: Date.now() },
      { new: true }
    );

    if (!updatedDisease) {
      return res.status(404).json({ message: 'Disease not found' });
    }
    res.json(updatedDisease);
  } catch (error) {
    res.status(500).json({ message: 'Failed to update disease', error: error.message });
  }
};


const deleteDiseaseByUserId = async (req, res) => {
  const { userId,name } = req.params
  try {
    const disease = await Disease.findOneAndDelete({ userId,name });

    if (!disease) {
      return res.status(404).json({ message: 'Disease not found' });
    }

    res.json({ message: 'Disease deleted' });
  } catch (error) {
    res.status(500).json({ message: 'Failed to delete disease', error: error.message });
  }
};

module.exports = { addDisease, getDiseasesByUserId, getDiseaseByNameAndUserId, updateDiseaseByUserId, deleteDiseaseByUserId };
