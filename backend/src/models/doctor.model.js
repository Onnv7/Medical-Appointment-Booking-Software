import mongoose from "mongoose";
import Booking from "./booking.model.js"

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
        type: Date
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
    specialist: {
        type: mongoose.Types.ObjectId,
        ref: "Specialist"
    },
    // start: {
    //     type: Number,
    //     default: 0
    // }
}, { timestamps: true });

doctorSchema.virtual('rating').get(async function () {
    let rating = 0
    try {
        rating = await Booking.aggregate([
            { $match: { doctorId: this._id } },
            { $group: { _id: null, avgRating: { $avg: '$rating' } } }
        ])
            .exec()
            .then(result => result.length ? result[0].avgRating : 0)
    } catch (error) {

    }
    finally {
        return rating;
    }
});


export default mongoose.model("Doctor", doctorSchema);