import mongoose from "mongoose";
const bookingSchema = mongoose.Schema({
    patient: {
        type: mongoose.Types.ObjectId,
        ref: "Patient",
        required: true,
    },
    doctor: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor",
        required: true,
    },
    message: {
        type: String,
    },
    status: {
        type: String,
        enum: ["waiting", "accepted", "denied", "succeeded"],
        default: "waiting"
    },
    advice: {
        type: String,
        default: ""
    },
    review: {
        type: mongoose.Types.ObjectId,
        ref: "Review",
        default: null
    },
    // star: {
    //     type: Number,
    // },
    time: {
        type: String,
        required: true,
    }
}, { timestamps: true });
bookingSchema.index({ doctor: 1, time: 1, patient: 1 }, { unique: true });

export default mongoose.model("Booking", bookingSchema);