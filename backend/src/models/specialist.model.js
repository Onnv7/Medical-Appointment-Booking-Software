import mongoose from "mongoose";

const specialistSchema = mongoose.Schema({
    name: {
        type: String,
        unique: true,
    },
    imageUrl: {
        type: String
    }
}, { timestamps: false });

export default mongoose.model("Specialist", specialistSchema);