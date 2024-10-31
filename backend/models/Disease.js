const mongoose = require('mongoose');



const DiseaseSchema = new mongoose.Schema({

    name: {
        type: String,
        required: true,
    },
    symptoms: {
        type: String,
        required: true,
    },
    severity: {
        type: String,
        enum: ['Mild', 'Moderate', 'Severe'],
        default: 'Moderate'
    },
    notes: {
        type: String,
        default: '',
    },
    treatment: {
        medications: [{

             name:String,
             dosage:String,
             frequency:String, //'Twice daily'
             duration: String,  // '2 weeks'

            },


        ],
        lifestyleRecommendations:{
            type:String, // "Avoid sugar and processed foods."
        }


    },
    status:{
        type:String,
        enum: ['Ongoing', 'Recovered','Under Observation'],
        default:'Ongoing',
    },

    diagnosedAt:{
        type:Date,
        default:Date.now,
    },
    lastUpdated:{
        type:Date,
        default:Date.now,
    }






})

DiseaseSchema.pre('save', function (next) {
    this.lastUpdated = Date.now(); // Automatically update lastUpdated before saving
    next();
  });

  
  module.exports = mongoose.model('Disease', DiseaseSchema);
