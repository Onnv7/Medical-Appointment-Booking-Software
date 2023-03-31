import mongoose from "mongoose";
import Review from "./booking.model.js"

const doctorSchema = mongoose.Schema({
    email: {
        type: String,
        unique: true
    },
    password: {
        type: String
    },
    name: {
        type: String
    },
    gender: {
        type: String,
        enum: ["male", "female"],
    },
    birthDate: {
        type: String
    },
    avatarUrl: {
        type: String
    },
    phone: {
        type: String
    },
    introduction: {
        type: String
    },
    clinicName: {
        type: String
    },
    clinicAddress: {
        type: String
    },
    price: {
        type: Number
    },
    specialistId: {
        type: mongoose.Types.ObjectId,
        ref: "Specialist"
    }
}, { timestamps: true });

doctorSchema.virtual('rating').get(function () {
    return this._rating || (
        this._rating = Review.aggregate([
            { $match: { doctor_id: this._id } },
            { $group: { _id: null, avgRating: { $avg: '$rating' } } }
        ])
            .exec()
            .then(result => result.length ? result[0].avgRating : 0)
    );
});


export default mongoose.model("Doctor", doctorSchema);