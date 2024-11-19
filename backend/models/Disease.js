const mongoose = require('mongoose');

const DiseaseSchema = new mongoose.Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true,
    },
    name: {
        type: String,
        required: true,
    },

    medicine: {
        type: String,
        required: true,

    },
    prescriptionTimes: {
        morning: {
            type: Boolean,
            default: false
        },
        afternoon: {
            type: Boolean,
            default: false
        },
        night: {
            type: Boolean,
            default: false
        },
    },
    prescription: {
        type: String,
        default: '',
    },
    notificationTime: {
        type: String,
        default: '',
    },
    diagnosedAt: {
        type: Date,
        default: Date.now,
    },
    lastUpdated: {
        type: Date,
        default: Date.now,
    }
})
DiseaseSchema.pre('save', function (next) {
    this.lastUpdated = Date.now();
    next();
});

module.exports = mongoose.model('Disease', DiseaseSchema);
