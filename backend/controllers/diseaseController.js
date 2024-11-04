const Disease=require('../models/Disease')





const addDisease = async(req,res) => {

const  {name,symptoms,severity,notes,treatment,status,diagnosedAt}=req.body

try {
    const newDisease = new Disease({
      name,
      symptoms,
      severity,
      notes,
      treatment,
      status,
      diagnosedAt,
    });
    await newDisease.save();
    res.status(201).json(newDisease);
} catch (error) {
    res.status(500).json({ message: 'Failed to add disease', error: error.message });
  }



 
} 

const getDiseaseByName = async (req, res) => {
  const { name } = req.params; // Get the name from the request parameters

  try {
      const disease = await Disease.findOne({ name }); // Find the disease by name

      if (!disease) {
          return res.status(404).json({ message: 'Disease not found' });
      }

      res.json(disease);
  } catch (error) {
      res.status(500).json({ message: 'Failed to retrieve disease', error: error.message });
  }
};


const getAllDiseases = async (req, res) => {
  try {
    const diseases = await Disease.find(); // Fetch all diseases
    res.json(diseases); // Return all diseases in JSON format
  } catch (error) {
    res.status(500).json({ message: 'Failed to retrieve diseases', error: error.message });
  }
};

const updateDisease = async (req, res) => {
    const { name, symptoms, severity, notes, treatment, status } = req.body;
  
    try {
      const updatedDisease = await Disease.findOneAndUpdate(
        {name},
        { name, symptoms, severity, notes, treatment, status, lastUpdated: Date.now() },
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


const deleteDisease = async (req, res) => {
  const { name } = req.params
    try {
      const disease = await Disease.findOneAndDelete({name});
  
      if (!disease) {
        return res.status(404).json({ message: 'Disease not found' });
      }
  
      res.json({ message: 'Disease deleted' });
    } catch (error) {
      res.status(500).json({ message: 'Failed to delete disease', error: error.message });
    }
  };
  
  module.exports = { addDisease,getDiseaseByName,getAllDiseases, updateDisease, deleteDisease };